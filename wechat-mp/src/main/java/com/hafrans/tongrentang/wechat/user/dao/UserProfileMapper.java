package com.hafrans.tongrentang.wechat.user.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.hafrans.tongrentang.wechat.user.domain.entity.UserProfile;

@Mapper
public interface UserProfileMapper {

	
	@Select("select id, nick_name as nickName, gender, language, city, province, country, avatarurl, appid, phone, phone_country_code as phoneCountryCode, phone_pure as phonePure, email from user_profile where id = #{id} ")
    public UserProfile queryByUserProfileId(long id);	
	
	@Update("update user_profile set "
		  + "nick_name  = #{nickName},"
		  + "gender = #{gender},"
		  + "language = #{language},"
		  + "city = #{city},"
		  + "province = #{province},"
		  + "country = #{country},"
		  + "avatarurl = #{avatarUrl},"
		  + "appid = #{appid},"
		  + "phone = #{phone},"
		  + "phone_country_code = #{phoneCountryCode},"
		  + "phone_pure = #{phonePure},"
		  + "email = #{email}"
		  + "where id = #{id}")
	public int update(UserProfile userProfile);
	
	
	@Insert("insert into user_profile (nick_name, gender, language, city, province,"
			+ "country, avatarurl, appid, phone, phone_country_code, phone_pure, email)"
			+ "VALUES ("
			+ "#{nickName}, #{gender}, #{language}, #{city}, #{province}, #{country}, #{avatarUrl}, "
			+ "#{appid}, #{phone}, #{phoneCountryCode},#{phonePure}, #{email} )")
	public int create(UserProfile userProfile);
	
	@Insert("insert into user_profile (nick_name, gender, language, city, province,"
			+ "country, avatarurl, appid, phone, phone_country_code, phone_pure, email)"
			+ "VALUES ("
			+ "#{nickName}, #{gender}, #{language}, #{city}, #{province}, #{country}, #{avatarUrl}, "
			+ "#{appid}, #{phone}, #{phoneCountryCode},#{phonePure}, #{email} )")
	@SelectKey(before=false,keyProperty="id",resultType=Long.class,statement="select currval('user_profile_id_seq') as id")
	public int createAndGetKey(UserProfile userProfile);
	
	//不提供delete

}
