package cn.joinhealth.model;

/**
 * Created by shilei on 2018/4/19.
 */
public class AccountApplicationUserModel {
//    CREATE TABLE `ssx_account_user` (
    /*开发者账号*/
    private Integer id;

    /*开发者名称*/
    private String name;

    private String contact_name;

    private String contact_mobile;

    private String state;

    private Integer register_addr_id;

    private String encrypted_password;

    private String email;

    /*状态*/
    private String salt;

    private String ctime;

    /*最新修改时间*/
    private String utime;

    private String role;

    private String status_flag;

    private String remark;

    private String expire_time;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_mobile() {
        return contact_mobile;
    }

    public void setContact_mobile(String contact_mobile) {
        this.contact_mobile = contact_mobile;
    }

    public Integer getRegister_addr_id() {
        return register_addr_id;
    }

    public void setRegister_addr_id(Integer register_addr_id) {
        this.register_addr_id = register_addr_id;
    }

    public String getEncrypted_password() {
        return encrypted_password;
    }

    public void setEncrypted_password(String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus_flag() {
        return status_flag;
    }

    public void setStatus_flag(String status_flag) {
        this.status_flag = status_flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }
}
