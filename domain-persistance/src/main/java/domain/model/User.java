package domain.model;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
	 
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="username")
    private String userName;
	
    @Column(name="password")
    private String password;
    
    @Column(columnDefinition = "TINYINT")
    private Boolean enabled;
    
    @Column(name="s3ProfilePhotoURL")
    private String s3ProfilePhotoURL;
    
    @Transient
    private Boolean adminFlag;
    
    @Transient
    private String role;
    
    @Transient
    private EntityInfo entityInfo;
    
    @Transient
    private EntityContact entityContact;
 
    public User() {
 
    }

	public User(Integer id, String userName, String password, Boolean enabled) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Boolean getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(Boolean adminFlag) {
		this.adminFlag = adminFlag;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getS3ProfilePhotoURL() {
		return s3ProfilePhotoURL;
	}

	public void setS3ProfilePhotoURL(String s3ProfilePhotoURL) {
		this.s3ProfilePhotoURL = s3ProfilePhotoURL;
	}

	public EntityInfo getEntityInfo() {
		return entityInfo;
	}

	public void setEntityInfo(EntityInfo entityInfo) {
		this.entityInfo = entityInfo;
	}

	public EntityContact getEntityContact() {
		return entityContact;
	}

	public void setEntityContact(EntityContact entityContact) {
		this.entityContact = entityContact;
	}
	
	
 
}
