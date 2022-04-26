package ru.flystar.travelrk.service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.VideoSnippet;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import ru.flystar.travelrk.domain.persistents.Video;
import ru.flystar.travelrk.repositories.VideoRepository;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 19.07.2017.
 */
@Log4j
@Service
public class YoutubeService {
  @Value("${youtube.apikey}")
  private String YOUTUBE_API_KEY;
  @Value("${youtube.thumbs}")
  private String YOUTUBE_THUMBS;
  private YouTube youtube;
  private final VideoRepository videoRepository;
  private final RegionService regionService;
  private final CategoryOfContentService categoryOfContentService;

  @Autowired
  public YoutubeService(VideoRepository videoRepository, RegionService regionService, CategoryOfContentService categoryOfContentService) {
    this.videoRepository = videoRepository;
    this.regionService = regionService;
    this.categoryOfContentService = categoryOfContentService;
  }

  @PostConstruct
  private void setup() throws ParseException {
    youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
      @Override
      public void initialize(HttpRequest request) throws IOException {
      }
    }).setApplicationName("TravelRK").build();
  }

  public List<Video> getVideoList() {
    return videoRepository.findAll();
  }

  @Transactional
  public Long removeById(String id) {
    return videoRepository.deleteByYoutubeId(id);
  }

  public Video getVideoById(String idForEdit) {
    return videoRepository.findByYoutubeId(idForEdit);
  }

  @Transactional
  public Video saveVideo(Video v) {
    return videoRepository.saveAndFlush(v);
  }

  @Transactional
  public String addVideo(String youtubeId) {
    YouTube.Videos.List listVideosRequest;
    if (videoRepository.findByYoutubeId(youtubeId) != null) {
      return "DUBLICAT";
    }
    try {
      listVideosRequest = youtube.videos().list("snippet").setKey(YOUTUBE_API_KEY).setId(youtubeId);
      VideoListResponse listResponse = listVideosRequest.execute();
      List<com.google.api.services.youtube.model.Video> videos = listResponse.getItems();
      if (videos.isEmpty()) {
        return "YTBERROR";
      }
      com.google.api.services.youtube.model.Video video = videos.get(0);
      VideoSnippet snippet = video.getSnippet();
      String title = snippet.getTitle();
      if (title.length() > 254) title = title.substring(0, 254);
      Video ytbVideo = new Video(youtubeId, title);
      ytbVideo.setRegion(regionService.getRegionByName("crimea"));
      ytbVideo.setCategoryOfContent(categoryOfContentService.getCategoryOfContentByName("natural"));
      copyTumbToLocalDir(youtubeId, snippet);
      videoRepository.saveAndFlush(ytbVideo);
      return "SUCCESS";
    } catch (IOException e) {
      e.printStackTrace();
      return "ERROR";
    }
  }

  private void copyTumbToLocalDir(String youtubeId, VideoSnippet snippet) throws IOException {
    ThumbnailDetails thumbnails = snippet.getThumbnails();
    Thumbnail maxres = thumbnails.getMaxres();
    Thumbnail high = thumbnails.getHigh();
    Thumbnail medium = thumbnails.getMedium();
    Thumbnail standard = thumbnails.getStandard();
    Thumbnail aDefault = thumbnails.getDefault();
    String strUrl = "";
    File imgFile = new File(YOUTUBE_THUMBS + youtubeId + ".jpg");
    if (aDefault != null) strUrl = aDefault.getUrl();
    if (standard != null) strUrl = standard.getUrl();
    if (medium != null) strUrl = medium.getUrl();
    if (high != null) strUrl = high.getUrl();
    if (maxres != null) strUrl = maxres.getUrl();
    URL url = new URL(strUrl);
    FileCopyUtils.copy(FileCopyUtils.copyToByteArray(url.openStream()), imgFile);
  }
}
