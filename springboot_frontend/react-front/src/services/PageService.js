import axios from 'axios'

class PageService{
    getPage(page,size){
        return axios.get(`http://localhost:8080/api/page?page=${page}?size=${size}`)
    }
}

export default new PageService();