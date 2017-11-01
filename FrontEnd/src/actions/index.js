import axios from 'axios';

export const GET_QR='get_qr';
export const TEST='test';
export const VERIFY='verity_auth';
export const CODE_CORRECT='code_correct';
export const SCRATCH_CODES='scratch_codes';
export const REFRESH_CODES='refresh_codes';
export const REGISTRY='registry';

const ip = '172.30.190.169';
const port = '5000';

const BACKEND = `http://${ip}:${port}`;



export function getQR(id){
    const request = axios.get(`${BACKEND}/registry/qr/${id}`);
    return {
        type: GET_QR,
        payload: request
    };
}

export function verifyAuth(id){
    const auth = axios.get(`${BACKEND}/twoFactor/${id}`); 
    return{
        type:VERIFY,
        payload:auth
    };
}

export function registryUser(id, callback){
    const regis = axios.post(`${BACKEND}/registry/onAuth/${id}`).then((res) => callback(res));
    return{
        type:REGISTRY,
        payload:regis
    };
}

export function codeCorrect(id, code, callback){
    const correct = axios.get(`${BACKEND}/twoFactor/${id}/${code}`).then((res) => callback(res));
    return{
        type:CODE_CORRECT,
        payload:correct
    };
}

export function listScratchCodes(id){
    const list = axios.get(`${BACKEND}/emergencyCodes/${id}`);
    return{
        type:SCRATCH_CODES,
        payload:list
    }
}

export function refreshScratchCodes(id){
    const list = axios.get(`${BACKEND}/emergencyCodes/generateCodes/${id}`);
    return{
        type:REFRESH_CODES,
        payload:list
    }
}