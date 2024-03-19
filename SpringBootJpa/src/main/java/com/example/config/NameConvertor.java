package com.example.config;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.example.user.entity.Name;

@Converter
public class NameConvertor implements AttributeConverter<Name, String> {

	@Override
	public String convertToDatabaseColumn(Name name) {

		return Name.name(name.getFirsrtName(), name.getLastName());
	}

	@Override
	public Name convertToEntityAttribute(String dbData) {
		if(dbData!= null && !dbData.isEmpty()) {
		String[] split = dbData.split(":");
		if(split.length==2) {
		return new Name(split[0], split[1]);
		}
		return new Name(split[0], "NA");
		}
		return new Name("NA", "NA");
		
	}

}
