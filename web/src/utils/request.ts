import axios, {AxiosInstance, AxiosError, AxiosResponse, AxiosRequestConfig} from 'axios';
import {ElMessage} from "element-plus";

export interface Page {
    curr: number,
    size: number,
    navigatePages?: number
}

const service:AxiosInstance = axios.create({
    timeout: 5000
});


service.interceptors.request.use(
    (config: AxiosRequestConfig) => {
        if (localStorage.getItem("token")) {
            (<any>config.headers).common['Authorization'] = "Bearer " + localStorage.getItem("token");
        }
        return config;
    },
    (error: AxiosError) => {
        return Promise.reject();
    }
);

service.interceptors.response.use(
    (response: AxiosResponse) => {
        if (response.status === 200) {
            if(response.data.code != 200){
                if(response.data.code == 403 && response.data.msg == "通行证过期了"){
                    ElMessage.error(response.data.msg);
                    setTimeout(function() {
                        window.location.href = "/login";
                    }, 1000);

                }
                ElMessage.error(response.data.msg);
                return Promise.reject(new Error(response.data.msg || 'Error'));
            }else{
                return response.data;
            }
        } else {
            return Promise.reject();
        }

    },
    (error: AxiosError) => {
        console.log(error);
        return Promise.reject();
    }
);


export default service;
