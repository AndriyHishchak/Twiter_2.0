package com.Spring.SpringLetsCode.service;

import com.Spring.SpringLetsCode.Model.Message;
import com.Spring.SpringLetsCode.Model.User;
import com.Spring.SpringLetsCode.Repo.RepoMessages;
import com.Spring.SpringLetsCode.domain.DTO.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class MessageServise {
    @Autowired
    private RepoMessages messagesRepo;

    public Page<MessageDTO> messagesList(Pageable pageable, String filter,User user) {
        if (filter != null && !filter.isEmpty()) {
          return messagesRepo.findByTag(filter, pageable,user);
        } else {
          return messagesRepo.findAll(pageable,user);
        }
    }

    public Page<MessageDTO> messagesListUser(Pageable pageable, User currentUser, User author) {
        return messagesRepo.findByUser(pageable,author,currentUser);
    }

    public Page<MessageDTO> findAll(Pageable pageable, User user) {
       return messagesRepo.findAll(pageable,user);
    }
}
