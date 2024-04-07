package com.example.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.example.config.NameConvertor;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "app_user")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myEhCache")
public class AppUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Convert(converter = NameConvertor.class)
	@Column(name = "fullName")
	private Name name;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dob;
	@NotBlank(message = "{username.not.blank}")
	@Column(unique = true)
	private String username;
	@NotBlank(message = "{password.not.blank}")
	private String password;

	@NotNull
	@Size(min = 8)
	@Column(unique = true)
	private String token;
	
	@Enumerated(EnumType.STRING)
	private UserType type;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "street", column = @Column(name = "address_street")),
			@AttributeOverride(name = "city", column = @Column(name = "address_city")),
			@AttributeOverride(name = "code", column = @Column(name = "address_code")) })
	private Address address;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myEhCache")
	@OneToMany(cascade = CascadeType.PERSIST , fetch = FetchType.LAZY)
	@JoinColumn(name="uid")
	private List<Car> cars;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myEhCache")
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "user_book", 
			  joinColumns = @JoinColumn(name = "u_id", referencedColumnName = "id"), 
			  inverseJoinColumns = @JoinColumn(name = "b_id", referencedColumnName = "id"))
    private Set<Book> books;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUser other = (AppUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppUser [id=" + id + ", name=" + name + ", dob=" + dob + ", username=" + username + ", password="
				+ password + ", token=" + token + ", address=" + address + ", cars=" + cars + ", books=" + books + "]";
	}
	
	

}
