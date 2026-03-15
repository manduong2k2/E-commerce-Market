const express = require('express');
const router = express.Router();
const axios = require('axios');

const fs = require('fs');
const multer = require('multer');
const file = multer({ dest: 'resource/temps/' });
const FormData = require('form-data');

const URL = process.env.BASE_URL +':'+ process.env.BRAND_SERVICE_PORT;

//Get all
router.get('/', async (req, res) => {
    try {
        console.log('get all brands triggered');
        const response = await axios.get(`${URL}/api/brands`);
        res.status(response.status).json(response.data);
    }
    catch (error) {
        res.send('ERR:'+error);
        console.log(URL);
    }
});

//Get by id
router.get('/:id', async (req, res) => {

    try {
        console.log(`get brand by id [${req.params.id}] triggered`);
        const response = await axios.get(`${URL}/api/brands/${req.params.id}`);
        res.status(response.status).json(response.data);
    }
    catch (error) {
        res.send(error);
        console.log(error);
    }
});

//Create
router.post('/', file.single('file'), async (req, res) => {
    try {
        const form = new FormData();
        let filePath = null;
        if (req.file) {
            filePath = req.file.path;
            form.append('file', fs.createReadStream(filePath), req.file.originalname);
        }
        Object.entries(req.body).forEach(([key, value]) => {
            form.append(key, value);
        });
        const response = await axios.post(`${URL}/api/brands`, form, {
            headers: form.getHeaders(),
        });
        if (filePath) {
            fs.unlinkSync(filePath);
        }
        res.status(response.status).json(response.data);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: error.message || 'Something went wrong' });
    }
});


//Update
router.put('/:id', file.single('file'), async (req, res) => {
    try {
        const filePath = req.file.path;
        const form = new FormData();
        form.append('file', fs.createReadStream(filePath), req.file.originalname);
        for (const key in req.body) {
            form.append(key, req.body[key]);
        }
        const response = await axios.put(`${URL}/api/brands/${req.params.id}`, form, {
            headers: {
                ...form.getHeaders()
            },
        });
        fs.unlinkSync(filePath);
        res.status(response.status).json(response.data);
    } catch (error) {
        res.send(error);
        console.log(error);
    }
});

//Delete by id
router.delete('/:id', async (req, res) => {
    try {
        const response = await axios.delete(`${URL}/api/brands/${req.params.id}`);
        res.status(response.status).json(response.data);
    } catch (error) {
        res.send(error);
        console.log(error);
    }
});

module.exports = router;