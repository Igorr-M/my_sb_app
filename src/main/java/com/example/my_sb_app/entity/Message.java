package com.example.my_sb_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Entity
public class Message {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @NotBlank(message = "Please fill the message")
   @Length(max = 2048, message = "Message too long (more than 2kB)")
   private String text;
   @Length(max = 255, message = "Tag too long (more than 255)")
   private String tag;

   public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
       }
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "user_id")
   private User author;
   private String filename;
   public Message() {}
    public String getAuthorName() {
        return (this.author != null) ? this.author.getUsername() : "<none>";
   }

   public Long getId() {
        return this.id;
   }

   public void setId(Long id) {
        this.id = id;
   }

   public String getText() {
        return this.text;
   }

   public void setText(String text) {
        this.text = text;
   }

   public String getTag() {
        return this.tag;
   }

   public void setTag(String tag) {
        this.tag = tag;
   }

   public User getAuthor() {
        return this.author;
   }

   public void setAuthor(User author) {
        this.author = author;
   }

   public String getFilename() {
        return this.filename;
   }

   public void setFilename(String filename) {
        this.filename = filename;
   }
    
}
