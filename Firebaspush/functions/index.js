'use-strict'


const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotificationToFCMToken = functions.firestore.document('Orders/{order_id}').onWrite(async (event) => {
    const order_id= event.after.get('Order_id');
    const shop_id = event.after.get('Shop_id');
    const user_id = event.after.get('User_id');
    const product_id=event.after.get('Product_id');
    const address_id=event.after.get('Address_id');
    let userDoc = await admin.firestore().doc(`Shop/`+shop_id).get();
    let fcmToken = userDoc.get('token');
    let shop_name=userDoc.get('name');

    console.log("shop_id :"+shop_id);
    console.log("user_id :"+user_id);
    console.log("fcmToken :"+fcmToken);
    console.log("order_id :"+order_id);

    let Doc = await admin.firestore().doc(`Users/`+user_id).get();
    let phone = Doc.get('Phone');
    let usertoken=Doc.get('token');
    console.log("Phone :"+phone);
    console.log("token :"+usertoken);

    var message = {
        notification: {
            title: "Order Update",
            body: "New Order from "+phone,
        },
        android: {
            notification: {
              click_action:"stop.one.sellerapp.firebasepushnotification.TARGETNOTIFICATION",
              show_in_foreground:true,
            }},
        data:{"Order_id":order_id,"Address_id":address_id,"Product_id":product_id,"User_id":user_id},
        token: fcmToken,
    }

    let response = await admin.messaging().send(message);
    console.log("First send");


    var msg = {
        notification: {
            title: "Order Update",
            body: "Order update from "+shop_name,
        },
        android: {
            notification: {
                click_action:"com.project.userapp.firebasepushnotification.TARGETNOTIFICATION",
                show_in_foreground:true,
            }},
        data:{"Order_id":order_id,"Address_id":address_id,"Product_id":product_id},
        token: usertoken,
    }

    let res = await admin.messaging().send(msg);
    console.log("Second send");
});