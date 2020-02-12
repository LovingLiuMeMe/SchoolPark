package cn.lovingliu.service.impl;

import cn.lovingliu.mapper.PicturesMapper;
import cn.lovingliu.pojo.Pictures;
import cn.lovingliu.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PicturesMapper picturesMapper;

    public Integer createPictures(Integer recordId, List<String> fileNameList){
        List<Pictures> picturesList = new ArrayList<>();
        for (int i = 0; i < fileNameList.size(); i++) {
            Pictures pictures = new Pictures();
            pictures.setRecordId(recordId);
            pictures.setPictureName(fileNameList.get(i));
            pictures.setPictureOrder(i);
            pictures.setCreatedTime(new Date());
            picturesList.add(pictures);
        }
        int count = picturesMapper.batchInsert(picturesList);
        return count;
    }
}
