const config = {
    baseUrl: 'http://localhost:8081',
    api: {
        captcha: '/user/captcha',
        login: '/user/login',
        register: '/user/register',
        logout: '/user/logout',
        getUserInfo: '/user/getinfo',
        authToken:'/user/token-auth',
        ip: '/system/ip',
        ShopInfo: "/element/info",
        code: '/user/code',
        selectUser:"/user/select-info",
        deleteUser:"/user/delete",
        updateUser:"/user/change",
        deleteShop:"/element/delete",
        uploadImg:"/system/img/base64",
        updateShop:"/element/change",
        insertShop:"/element/insert",
        getOrders:"/pay/getall",
        payInsert:"/pay/top-up",
        payMyinfo:"/pay/get",
        payEx:"/pay/exchange",
        payForOrder:"/pay/pay-order"
    },
    apiUrl: '/api'
}
export default config