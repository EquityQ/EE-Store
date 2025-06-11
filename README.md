# EquityQ's Shop


## The version you are currently browsing is outdated. If you want to view the latest version, please go to the `main` branch. 

## Project Introduction
EquityQ's Shop is an online store application based on Vue.js. Users can browse the product list, view product details, and add products to the shopping cart. 



## Technology Stack
- **Front-end Framework**: Vue.js
- **UI Library**: Element UI
- **State Management**: Vuex (optional)
- **Routing Management**: Vue Router (optional)
- **Build Tool**: Webpack / Vite (depending on the actual situation) 

## Installation and Operation
### Prerequisites
- Node.js (version 14.x or above is recommended)
- npm or yarn 

## Function Introduction
### Home Page
- Displays a list of products
- Each product card includes the product image, name, price, stock, and description
- Users can click the "Add to Cart" button to add the product to the shopping cart 

### Shopping Cart
- Displays the list of added items
- Users can view the quantity of items and the total price
- Provides a function to clear the shopping cart 

## Environment Variables
- **VUE_APP_API_URL**: Base URL for API requests
- **VUE_APP_ENV**: Current environment (development, production)


# API Documentation

## Overview

This document provides an overview of the available API endpoints for the application, including their request methods, parameters, and expected responses.

## Endpoints

### User Authentication

#### `POST /api/user/login`

- **Description**: Authenticates a user and returns a token.
- **Request Body**:
  - `username` (string): The user's username.
  - `password` (string): The user's password.
  - `captcha` (string): Captcha code for verification.
- **Response**:
  - `Code` (int): Status code (200 for success).
  - `Reason` (string): Message indicating success or failure.
  - `token` (string, optional): Authentication token if successful.
  - `permission` (string, optional): User's permission level.

#### `POST /api/user/token-auth`

- **Description**: Validates a user's token.
- **Headers**:
  - `token` (string): The user's authentication token.
- **Response**:
  - `Code` (int): Status code (200 for success).
  - `Response` (string): Message indicating success or failure.
  - `permission` (string, optional): User's permission level.

#### `POST /api/user/logout`

- **Description**: Logs out a user and invalidates the session.
- **Headers**:
  - `token` (string): The user's authentication token.
- **Response**:
  - `Code` (int): Status code (200 for success).
  - `Response` (string): Message indicating success or failure.

### User Management

#### `POST /api/user/register`

- **Description**: Registers a new user.
- **Request Body**:
  - `username` (string): The desired username.
  - `password` (string): The desired password.
  - `captcha` (string): Captcha code for verification.
- **Response**:
  - `Code` (int): Status code (200 for success).
  - `Reason` (string): Message indicating success or failure.
  - `token` (string, optional): Authentication token if successful.
  - `permission` (string, optional): User's permission level.

#### `POST /api/user/select-info`

- **Description**: Retrieves user information.
- **Headers**:
  - `token` (string): The user's authentication token.
- **Response**:
  - `Code` (int): Status code (200 for success).
  - `users` (array): List of user information.

### Payment

#### `POST /api/pay/pay-order`

- **Description**: Processes payment for an order.
- **Headers**:
  - `token` (string): The user's authentication token.
- **Request Body**:
  - `items` (array): List of `CartItem` objects representing the order.
- **Response**:
  - `Code` (int): Status code (200 for success).
  - `Response` (string): Message indicating success or failure.

#### `POST /api/pay/top-up`

- **Description**: Tops up a user's account balance.
- **Headers**:
  - `token` (string): The user's authentication token.
- **Request Parameters**:
  - `value` (double): The amount to top up.
- **Response**:
  - `Code` (int): Status code (200 for success).
  - `Response` (string): Message indicating success or failure.

#### `POST /api/pay/get`

- **Description**: Retrieves the user's account balance.
- **Headers**:
  - `token` (string): The user's authentication token.
- **Response**:
  - `Code` (int): Status code (200 for success).
  - `Response` (string): Message indicating success or failure.
  - `Value` (double): The user's current balance.

### Miscellaneous

#### `GET /api/ip`

- **Description**: Retrieves the client's IP address.
- **Response**:
  - `Code` (int): Status code (200 for success).
  - `ip` (string): The client's IP address.

#### `POST /api/element/info`

- **Description**: Retrieves information about all elements.
- **Response**:
  - `Code` (int): Status code (200 for success).
  - `elements` (array): List of elements.

## Error Codes

- `200`: Success
- `400`: Bad Request
- `401`: Unauthorized
- `402`: Payment Required
- `404`: Not Found
- `500`: Internal Server Error

## Notes

- All requests and responses are in JSON format.
- Ensure that the `token` is included in the headers for endpoints that require authentication.
