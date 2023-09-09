package com.example.demo.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.demo.entities.EntitiesClass;

@Component
public class EntitiesItemProcessor implements ItemProcessor<EntitiesClass, EntitiesClass> {

	@Override
	public EntitiesClass process(EntitiesClass item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}
