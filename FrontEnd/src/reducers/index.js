import { combineReducers } from 'redux';
import {reducer as formReducer} from 'redux-form';
import ApiReducer from './api_reducer';


const rootReducer = combineReducers({
    api: ApiReducer,
    form: formReducer
});

export default rootReducer;
