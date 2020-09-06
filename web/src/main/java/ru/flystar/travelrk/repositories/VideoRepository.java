package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.Video;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.11.2017.
 */
public interface VideoRepository extends JpaRepository<Video, Long> {

    Long deleteByYoutubeId(String youtubeId);

    Video findByYoutubeId(String youtubeId);

    Video saveAndFlush(Video video);
}
