package com.example.asecaserver.service;

import com.example.asecaserver.model.Player;

public interface PlayerService {
    Player findById(Long id) throws Exception;
}
