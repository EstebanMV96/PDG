import {VERIFY} from '../actions';
// import _ from 'lodash';

export default function(state={},action){
    switch(action.type){
        case VERIFY:
            return {...state,"TEST":"HOLA TEST :v"};

        default:
            return state;
    }
}