package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.protobuf.ByteString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@XmlRootElement
public class User{

    @NotNull
    private int userId;
    @NotBlank @Length(min=2, max=50)
    private String userName;
    @Pattern(regexp = ".+@.+\\.[a-z]+")
    private String email;
    @NotNull
    private String password;
    private ByteString hashedPassword;
    private ByteString salt;

    public User(){
        // Needed for Jackson deserialisation
    }

    public User(int userId, String userName, String email){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    public User(int userId, String userName, String email, String password, ByteString hashedPassword, ByteString salt){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    @XmlElement
    @JsonProperty
    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }

    @XmlElement
    @JsonProperty
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

    @XmlElement
    @JsonProperty
    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    @XmlElement
    @JsonProperty
    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public ByteString getHashedPassword(){
        return hashedPassword = hashedPassword;
    }
    public void setHashedPassword(ByteString hashedPassword){
        this.hashedPassword = hashedPassword;
    }

    public ByteString getSalt(){
        return salt;
    }
    public void setSalt(ByteString salt){
        this.salt = salt;
    }

}
