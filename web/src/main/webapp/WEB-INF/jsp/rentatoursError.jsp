<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ошибка лицензии!</title>
    <meta name="viewport" content="width=320, initial-scale=1" />
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <style>
        html{ height:100%; }
        body{ height:100%; overflow:hidden; margin:0; padding:0; font-family:Arial, Helvetica, sans-serif; font-size:14px; font-weight:bold; color:#FFFFFF; background-color:#000000; }
        a{ color:#FFFFFF; text-decoration:none; }
        a:hover{ color:#FFFFFF; text-decoration:none; }
        .button{ display:block; position:relative; width:180px; height:53px; background:#3D3D3D; background-image:url('data:image/gif;base64,R0lGODlhCAAIAIEBAFVVVf///wAAAAAAACH/C05FVFNDQVBFMi4wAwEBAAAh+QQBBwABACwAAAAACAAIAAAIGwABBBgocGCAggcNJiSoECEAhw0NIlxIkSKAgAA7'); border-radius:8px; box-shadow:inset 0px 0px 2px #FFFFFF; margin:10px; }
        .buttonicon{ position:absolute; top:8px; left:8px; width:39px; height:39px; border:0;  }
        .buttontext{ position:absolute; left:55px; top:10px; }
        .smalltext{ font-size:10px; }
        .firefox{ background:url('data:image/png;base64,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'); }
        .chrome{ background:url('data:image/png;base64,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'); }
        .opera{ background:url('data:image/png;base64,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'); }
    </style>
</head>
<body>

<div style="width:100%;height:100%;">
    <table style="width:100%;height:100%;">
        <tr style="valign:middle;">
            <td>
                <div style="text-align:center;">
                    <h2><u>Ошибка лицензии!</u></h2>
                    <br>
                    Извените, но лицензия на данный контент ограничена!<br>
                    <br>
                    <br>
                    ${message}
                </div>
                <br>
                <br>
                <br>
                <br>
                <br>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
