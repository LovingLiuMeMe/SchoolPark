package cn.lovingliu.service;

import java.util.List;

public interface PictureService {
    Integer createPictures(Integer recordId, List<String> fileNameList);
}
