//package project.shop1.feature.quantityManagement.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import project.shop1.entity.Item;
//import project.shop1.feature.quantityManagement.repository.QuantityManagementRepository;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class QuantityManagementService {
//
//    private final QuantityManagementRepository quantityManagementRepository;
//
//    @Transactional
//    public void saveItem(Item item){
//        quantityManagementRepository.save(item);
//    }
//
//    public List<Item> findItems(){
//        return quantityManagementRepository.findAll();
//    }
//
//    public Item findOne(Long itemId){
//        return quantityManagementRepository.findOne(itemId);
//    }
//
//    /**
//     * stock 증가
//     */
//    @Transactional
//    public void addStock(String itemName, int quantity){
//        Item item = quantityManagementRepository.findItemByName(itemName);
//        item.setStockQuantity(item.getStockQuantity()+quantity);
//    }
//
//    @Transactional
//    public void removeStock(String itemName, int quantity){
//        Item item=quantityManagementRepository.findItemByName(itemName);
//        int restStock=item.getStockQuantity()-quantity;
//        if (restStock<0){
//            throw new NotEnoughStockException("need more stock");
//        }
//        item.setStockQuantity(restStock);
//    }
//}
