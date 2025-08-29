# 🗄️ Configuración de Base de Datos - FP Shopping Cart

## 📋 Verificación de Configuración

### **1. Docker Compose Configuration ✅**
El archivo `compose.yaml` está correctamente configurado:
- **MySQL 8.2.0** con autenticación moderna
- **Puerto**: 3310 (mapeado desde 3306)
- **Base de datos**: shoppingCartDB
- **Usuario**: keycloak
- **Contraseña**: keycloak
- **Volumen persistente**: mysql-data

### **2. Variables de Entorno**
Crear archivo `.env` basado en `env-example`:
```bash
# Copiar el archivo de ejemplo
cp env-example .env
```

### **3. Configuración de Spring Boot ✅**
- `spring.jpa.hibernate.ddl-auto=update` - Crea/actualiza tablas
- `spring.sql.init.mode=always` - Ejecuta data.sql siempre
- `spring.jpa.defer-datasource-initialization=true` - Espera a que Hibernate termine
- `spring.sql.init.continue-on-error=true` - Continúa si hay errores

### **4. Archivo data.sql ✅**
- **7 usuarios** predefinidos (3 admin, 4 user)
- **5 categorías** de productos
- **10 productos** de ejemplo
- **5 carritos** con items
- **5 órdenes** con items
- **4 facturas** de ejemplo
- **8 reseñas** de productos

## 🚀 Pasos para Ejecutar

### **Paso 1: Iniciar MySQL con Docker**
```bash
# Desde el directorio fpshoppingcart
docker-compose up -d

# Verificar que esté ejecutándose
docker ps | grep mysql
```

### **Paso 2: Verificar Conexión a MySQL**
```bash
# Conectar a MySQL
docker exec -it fpshoppingcart-mysql-1 mysql -u keycloak -pkeycloak

# Dentro de MySQL
USE shoppingCartDB;
SHOW TABLES;
SELECT COUNT(*) FROM users;
```

### **Paso 3: Ejecutar la Aplicación**
```bash
# Compilar y ejecutar
./mvnw clean compile
./mvnw spring-boot:run
```

### **Paso 4: Verificar Datos Cargados**
```bash
# Verificar usuarios
curl http://localhost:8080/api/users

# Verificar productos
curl http://localhost:8080/api/products

# Login con usuario admin
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin1&password=password123"
```

## 🔍 Verificación de Datos

### **Usuarios Disponibles:**
- **Admin**: `admin1` / `password123`
- **Admin**: `josephr2316` / `password123`
- **Admin**: `bobjohnson` / `password123`
- **User**: `user1` / `password123`
- **User**: `janedoe` / `password123`
- **User**: `alicesmith` / `password123`
- **User**: `evamiller` / `password123`

### **Categorías Disponibles:**
1. Electronics
2. Clothing
3. Books
4. Home and Kitchen
5. Sports and Outdoors

### **Productos Disponibles:**
- Smartphone X ($899.99)
- Laptop Pro ($1299.99)
- Wireless Headphones ($149.99)
- Casual T-shirt ($19.99)
- Running Shoes Elite ($79.99)
- Y más...

## ⚠️ Posibles Problemas y Soluciones

### **Problema 1: "Table not found"**
```bash
# Solución: Verificar que las tablas se crearon
docker exec -it fpshoppingcart-mysql-1 mysql -u keycloak -pkeycloak -e "USE shoppingCartDB; SHOW TABLES;"
```

### **Problema 2: "Connection refused"**
```bash
# Solución: Verificar que MySQL esté ejecutándose
docker ps | grep mysql
docker logs fpshoppingcart-mysql-1
```

### **Problema 3: "Access denied"**
```bash
# Solución: Verificar credenciales
docker exec -it fpshoppingcart-mysql-1 mysql -u root -proot -e "SELECT User, Host FROM mysql.user;"
```

## ✅ Estado Final Esperado

Después de ejecutar todos los pasos:
- ✅ **MySQL ejecutándose** en puerto 3310
- ✅ **Base de datos creada** shoppingCartDB
- ✅ **Tablas creadas** por Hibernate
- ✅ **Datos insertados** desde data.sql
- ✅ **Aplicación ejecutándose** en puerto 8080
- ✅ **API respondiendo** correctamente
- ✅ **Usuarios disponibles** para login

## 🎯 Comandos de Verificación Rápida

```bash
# 1. Verificar Docker
docker ps | grep mysql

# 2. Verificar base de datos
docker exec -it fpshoppingcart-mysql-1 mysql -u keycloak -pkeycloak -e "USE shoppingCartDB; SELECT COUNT(*) FROM users;"

# 3. Verificar aplicación
curl http://localhost:8080/api/products

# 4. Verificar login
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin1&password=password123"
```

**¡Todo debería funcionar perfectamente!** 🎉
