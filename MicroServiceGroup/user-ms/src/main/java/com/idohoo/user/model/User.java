package com.idohoo.user.model;

import java.util.Date;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_id
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    private Integer user_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.phone
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.password
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_name
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    private String user_name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.nick_name
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    private String nick_name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_level_id
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    private Integer user_level_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.email
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.create_time
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    private Date create_time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.status
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.status_time
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    private Date status_time;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_id
     *
     * @return the value of user.user_id
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_id
     *
     * @param user_id the value for user.user_id
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.phone
     *
     * @return the value of user.phone
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.phone
     *
     * @param phone the value for user.phone
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.password
     *
     * @return the value of user.password
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.password
     *
     * @param password the value for user.password
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_name
     *
     * @return the value of user.user_name
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_name
     *
     * @param user_name the value for user.user_name
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name == null ? null : user_name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.nick_name
     *
     * @return the value of user.nick_name
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public String getNick_name() {
        return nick_name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.nick_name
     *
     * @param nick_name the value for user.nick_name
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name == null ? null : nick_name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_level_id
     *
     * @return the value of user.user_level_id
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public Integer getUser_level_id() {
        return user_level_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_level_id
     *
     * @param user_level_id the value for user.user_level_id
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public void setUser_level_id(Integer user_level_id) {
        this.user_level_id = user_level_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.email
     *
     * @return the value of user.email
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.email
     *
     * @param email the value for user.email
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.create_time
     *
     * @return the value of user.create_time
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.create_time
     *
     * @param create_time the value for user.create_time
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.status
     *
     * @return the value of user.status
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.status
     *
     * @param status the value for user.status
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.status_time
     *
     * @return the value of user.status_time
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public Date getStatus_time() {
        return status_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.status_time
     *
     * @param status_time the value for user.status_time
     *
     * @mbggenerated Sun Jun 11 16:28:01 CST 2017
     */
    public void setStatus_time(Date status_time) {
        this.status_time = status_time;
    }
}