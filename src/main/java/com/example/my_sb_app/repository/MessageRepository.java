package com.example.my_sb_app.repository;

import com.example.my_sb_app.entity.Message;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer>  {
    List<Message> findByTag(String paramString);
}
