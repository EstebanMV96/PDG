import {GET_QR, VERIFY,CODE_CORRECT,SCRATCH_CODES, REFRESH_CODES,REGISTRY} from '../actions';
// import _ from 'lodash';

export default function(state={},action){
    switch(action.type){
        case GET_QR:   
            return {...state,"qr":action.payload.data};
        case VERIFY:
            return {...state,verify:action.payload.data};
        case CODE_CORRECT:
            return {...state,correct:action.payload};
        case SCRATCH_CODES:
            return {...state,codes:action.payload};
        case REFRESH_CODES:
            return {...state,codes:action.payload};
        case REGISTRY:
            return {...state,registry:action.payload}
            
        default:
            return state;
    }
}