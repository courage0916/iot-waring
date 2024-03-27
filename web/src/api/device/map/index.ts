import request, {Page} from '@/utils/request';

const path = '/api/device/map'

export const add = (params: object) => {
    return request({
        url: path,
        method: 'post',
        data: params
    });
};

export const del = (id: number) => {
    return request({
        url: path + '/' + id,
        method: 'delete'
    });
};

export const del_batch = (ids: number[]) => {
    return request({
        url: path + '/batch',
        method: 'delete',
        data: ids
    });
};

export const update = (params: object) => {
    return request({
        url: path,
        method: 'put',
        data: params
    });
};


export const select = (id: number) => {
    return request({
        url: path + '/' + id,
        method: 'get'
    });
};

export const page = (page: Page, query?: object) => {
    return request({
        url: path + '/' + page.curr + '/' + page.size + '/' + page.navigatePages + '/',
        method: 'get',
        params: query
    });
};

export const list = (query?: object) => {
    return request({
        url: path,
        method: 'get',
        params: query
    });
};


export const tree = (deptId: number) => {
    return request({
        url: path + '/tree/' + deptId,
        method: 'get'
    });
};

export const clone = (data: object) => {
    return request({
        url: path + '/clone',
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        data: data
    });
};

export const download = (params: object) => {
    return request({
        url: path + "/out/file",
        method: 'post',
        params: params,
        responseType: 'blob'
    });
};
