package com.shop.inventory.service;

import com.shop.inventory.dto.InventoryDTO;
import com.shop.inventory.model.Inventory;
import com.shop.inventory.repo.InventoryRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<InventoryDTO> getAllItems() {
        List<Inventory> itemList = inventoryRepo.findAll();
        return modelMapper.map(itemList, new TypeToken<List<InventoryDTO>>(){}.getType());
    }

    public InventoryDTO getInventoryById(Integer inventaryId){
        Inventory inventory = inventoryRepo.getItemById(inventaryId);
        return modelMapper.map(inventory,InventoryDTO.class);
    }

    public InventoryDTO saveItem(InventoryDTO inventoryDTO) {
        inventoryRepo.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    public InventoryDTO updateItem(InventoryDTO inventoryDTO) {
        inventoryRepo.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    public String deleteItem(Integer itemId) {
        inventoryRepo.deleteById(itemId);
        return "Item deleted";
    }

    public InventoryDTO getItemById(Integer itemId) {
        Inventory item = inventoryRepo.getItemById(itemId);
        return modelMapper.map(item, InventoryDTO.class);
    }
}
