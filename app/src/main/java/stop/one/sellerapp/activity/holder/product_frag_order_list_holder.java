package stop.one.sellerapp.activity.holder;

public class product_frag_order_list_holder {

    public product_frag_order_list_holder(){}

    String Name,Brand,Category,Description,Image,Item,Product_id,Price,Shop_id,Status,Address_id,User_id;

    public product_frag_order_list_holder(String name, String brand, String category, String description, String image, String item, String product_id, String price, String shop_id, String status, String address_id, String user_id) {
        Name = name;
        Brand = brand;
        Category = category;
        Description = description;
        Image = image;
        Item = item;
        Product_id = product_id;
        Price = price;
        Shop_id = shop_id;
        Status = status;
        Address_id = address_id;
        User_id = user_id;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getAddress_id() {
        return Address_id;
    }

    public void setAddress_id(String address_id) {
        Address_id = address_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String Product_id) {
        this.Product_id = Product_id;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getShop_id() {
        return Shop_id;
    }

    public void setShop_id(String shop_id) {
        Shop_id = shop_id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
