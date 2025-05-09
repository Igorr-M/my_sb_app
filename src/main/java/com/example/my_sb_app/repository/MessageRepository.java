package com.example.my_sb_app.repository;

import com.example.my_sb_app.entity.Message;
import com.example.my_sb_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer>  {
    Page<Message> findAll(Pageable pageable);
    Page<Message> findByTag(String paramString, Pageable pageable);
    Page<Message> findByAuthor(User user, Pageable pageable);

}
