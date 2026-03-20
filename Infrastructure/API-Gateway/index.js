require('dotenv').config();``
var express = require('express');
const morgan = require('morgan');
const cors = require('cors');

var app = express();

app.use(cors());
app.use(express.json());
app.use(morgan('dev'));

const productRoutes = require('./routes/product');
const brandRoutes = require('./routes/brand');
const categoryRoutes = require('./routes/category');

app.use('/api/products', productRoutes);
app.use('/api/brands', brandRoutes);
app.use('/api/categories', categoryRoutes);


const PORT = process.env.PORT || 3000;

app.listen(PORT ,()=>{
    try{
        console.log('application started on port ' + PORT);
    } catch (error){
        console.log(error);
    }
})