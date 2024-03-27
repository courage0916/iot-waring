import request, {Page} from '@/utils/request';

const path = '/api/user'


export const getUser = () =>{
    return request({
        url: path + '/curr/user',
        method: 'get'
    });
}
export const login = (username:string ,password:string) => {

    const formData = new FormData();
    formData.append("username",username)
    formData.append("password",password)
    return request({
        url:  '/api/auth/login',
        method: 'post',
        data: formData
    });
}
export const page = (page: Page , query?: object) => {
    return request({
        url: path + '/' + page.curr + '/' + page.size + '/' + page.navigatePages ,
        method: 'post',
        data: query
    });
};

export const list = (query?:object) => {
    return request({
        url: path,
        method: 'get',
        params:query
    });
};

export const select = (id: number) => {
    return request({
        url: path + '/' + id,
        method: 'get'
    });
};

export const add = (params: object) => {
    return request({
        url: path,
        method: 'post',
        data: params
    });
};


export const del = (id: number) => {
    return request({
        url: path + '/' + id ,
        method: 'delete'
    });
};

export const update = (params: object) => {
    return request({
        url: path,
        method: 'put',
        data: params
    });
};

export const setting = (setting: object) => {
    return request({
        url: path+ '/setting' ,
        method: 'post',
        data: setting
    });
};

export const getSetting = (setting: object) => {
    return request({
        url: path+ '/setting' ,
        method: 'get'
    });
};

export const loginOut = (token: string) => {
    const formData = new FormData();
    formData.append("token",token);

    return request({
        url: '/api/auth/login/out' ,
        method: 'post',
        data:formData
    });
};

export const heartbeat = () => {

    return request({
        url: '/api/auth/heartbeat' ,
        method: 'post'
    });
};