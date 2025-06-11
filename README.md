# CloudJavaWeb

## Project Structure

```
CloudJavaWeb-main/
├── Vue/
│   ├── public/
│   │   └── index.html
│   └── src/
│       ├── Vue-router/
│       │   └── RouterConfig.js
│       ├── class/
│       │   └── config.js
│       ├── components/
│       │   ├── DataLocal.vue
│       │   ├── FeetCop.vue
│       │   └── TopperCop.vue
│       ├── views/
│       │   ├── payment/
│       │   │   └── LocalPage.vue
│       │   ├── shop/
│       │   │   ├── CartPage.vue
│       │   │   ├── InsertShop.vue
│       │   │   ├── OrderPage.vue
│       │   │   ├── ShopCom.vue
│       │   │   └── UpdateShop.vue
│       │   ├── user/
│       │   │   ├── CodeInv.vue
│       │   │   ├── UserLogin.vue
│       │   │   └── UserRegister.vue
│       │   └── LocalHost.vue
│       ├── App.vue
│       └── main.js
│   ├── babel.config.js
│   ├── jsconfig.json
│   ├── package-lock.json
│   ├── package.json
│   └── vue.config.js
├── src/
│   └── main/
│       ├── java/
│       │   └── org/
│       │       └── example/
│       │           └── javawebv2/
│       │               └── com/
│       │                   └── v2/
│       │                       ├── Controller/
│       │                       │   ├── ElementController.java
│       │                       │   ├── PayController.java
│       │                       │   ├── SystemController.java
│       │                       │   └── UserController.java
│       │                       ├── Model/
│       │                       │   ├── CartItem.java
│       │                       │   ├── MyShopElement.java
│       │                       │   ├── Mydata.java
│       │                       │   ├── OrderRequest.java
│       │                       │   ├── Orders.java
│       │                       │   ├── User.java
│       │                       │   └── element.java
│       │                       ├── Service/
│       │                       │   └── ... 3 files, 0 dirs not shown
│       │                       ├── api/
│       │                       │   └── ... 1 files, 0 dirs not shown
│       │                       ├── mapper/
│       │                       │   └── ... 4 files, 0 dirs not shown
│       │                       └── utils/
│       │                           └── ... 2 files, 0 dirs not shown
│       │                   └── JavaWebV2Application.java
│       └── resources/
│           ├── static/
│           │   ├── css/
│           │   ├── js/
│           │   └── index.html
│           └── application.properties
├── README.md
├── cloud.sql
├── mvnw.cmd
└── pom.xml

```

## Key Features

- **User Registration and Login**
- **Role-Based Access Control (user and admin)**
- **Data Display and Management**
- **CAPTCHA Verification**
- **Token-Based Session Management**

## Technology Stack

### Backend
- Java 21
- Spring Boot
- MySQL
- JWT (JSON Web Token) for authentication

### Frontend
- Vue.js 3
- Element Plus
- Axios for HTTP requests
- Vuex for state management

### Build Tools
- Maven
- Node.js and npm

## Installation and Running

### Backend

1. Make sure you have Java 21 and Maven installed.
2. Navigate to the project root directory.
3. Run the following command to build and start the backend service:
4. By default, the backend service will run on `http://localhost:8080`.

### Frontend

1. Ensure that Node.js and npm are installed.
2. Navigate to the `Vue` directory.
3. Install dependencies: `npm install vue-cli`
4. Start the frontend development server: `npm run serve`
5. By default, the frontend service will run on `http://localhost:8081`.

## Configuration

### Backend Configuration

The backend configuration file is located at `src/main/resources/application.properties`. You can modify database connections, port settings, and other configurations as needed.

### Frontend Configuration

The frontend configuration file is located at `Vue/src/class/config.js`. You can modify API base URLs and other configurations as needed.

## API Documentation

Backend API documentation can be automatically generated via Swagger. After starting the backend service, visit `http://localhost:8080/swagger-ui.html` to view the API documentation.

## Contribution

Contributions are welcome! Please follow these steps:

1. Fork this repository.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Create a Pull Request.

## License

This project uses the [MIT](https://choosealicense.com/licenses/mit/) license.

## Contact

- Email: eq@eioe.fun
- GitHub: [https://github.com/yourusername](https://github.com/EquityQ)

Thank you for using CloudJavaWeb! If you have any questions or suggestions, please feel free to contact us.
