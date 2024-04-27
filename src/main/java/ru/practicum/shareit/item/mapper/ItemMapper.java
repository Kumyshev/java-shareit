package ru.practicum.shareit.item.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemMapper {
    ModelMapper mapper = new ModelMapper();

    public ItemDto toDto(Item item) {
        return mapper.map(item, ItemDto.class);
    }

    public Item toItem(ItemDto itemDto) {
        return mapper.map(itemDto, Item.class);
    }

    public Item toUpdateItem(ItemUpdateDto itemUpdateDto, Item item) {
        mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.LOOSE);
        mapper.map(itemUpdateDto, item);
        return item;
    }
}
