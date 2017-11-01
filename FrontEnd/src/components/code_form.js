import React, {Component} from 'react';

import {Field, reduxForm} from 'redux-form';

class Login extends Component{

    renderField(field){
        const {meta: {touched, error}} = field;
        const className = `form-group ${touched && error ? 'has-danger' : ''}`;

        return(
            <div className={className}>
                <input
                    className="form-control"
                    type="password"
                    onFocus="true"
                    placeholder="Code"
                    {...field.input}
                />
                <div className="text-help">
                    {touched ? error:''}
                </div>
            </div>
        );
    }

    onSubmit(values){
        this.props.history.push('/'); 
    }

    render(){
        const {handleSubmit}=this.props;
    
        return(
            <form onSubmit={handleSubmit(this.onSubmit.bind(this))}>
                
                <Field
                    name="codigo"
                    component={this.renderField}
                />
                <button type="submit" className="btn btn-primary">Acceder</button>
            </form>
        );
    }
}

function validate(values){
    const errors = {};
    console.log(values.codigo);
    if(values.codigo){
        if(values.codigo.length < 5){
            errors.codigo = 'Code must have 5 numbers';
        }
    }
    if(!values.codigo){
        errors.codigo = "Enter a code!";
    }

    return errors;
}



export default reduxForm({
    validate,
    form: 'PostLogin'
})(Login);