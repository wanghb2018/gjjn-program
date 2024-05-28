package com.mochen.controller;

import com.mochen.model.GameMap;
import com.mochen.model.Junxian;
import com.mochen.model.KeyanSJ;
import com.mochen.model.RoleSJ;
import com.mochen.service.GameMapService;
import com.mochen.service.JunxianService;
import com.mochen.service.KeyanSJService;
import com.mochen.service.RoleSJService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConfigController {
    @Autowired
    RoleSJService roleSJService;
    @Autowired
    GameMapService gameMapService;
    @Autowired
    JunxianService junxianService;
    @Autowired
    KeyanSJService keyanSJService;


    @GetMapping("/allRoleSJ")
    public List<RoleSJ> getAllRoleSj() {
        return roleSJService.getAll();
    }

    @GetMapping("/allGameMap")
    public List<GameMap> getAllGameMap() {
        return gameMapService.getAllGameMap();
    }

    @GetMapping("/allJunxian")
    public List<Junxian> getAllJunxian() {
        return junxianService.getAllJunxian();
    }

    @GetMapping("/allKeyanSj")
    public List<KeyanSJ> getAllKeyanSj() {
        return keyanSJService.getAll();
    }
}
