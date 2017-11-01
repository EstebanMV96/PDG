import React,{Component} from 'react'
import { Field, reduxForm } from 'redux-form'
import logo from '../logo4.svg';
import '../App.css';

import {connect} from 'react-redux';
import {codeCorrect} from '../actions';

import AlertContainer from 'react-alert'


class SyncValidationForm extends Component{

    constructor(props){
      super(props);
      this.state = {code:''};

      this.onInputChange = this.onInputChange.bind(this);
    }
    //OPCIONES PARA EL MENSAJE DE VALIDACIÓN DEL CODIGO
    alertOptions = {
      offset: 14,
      position: 'top left',
      theme: 'light',
      time: 4000,
      transition: 'fade'
    }
   
    

    onInputChange(event){
      this.setState({code:event.target.value});      
    }


    handleSubmit(){
      const id = this.props.location.state.id;
      const url = this.props.location.state.url;
      this.props.codeCorrect(id, this.state.code, (response) => {
        const correct = response.data; 
          if(correct){
            if(correct==="OK"){
              this.msg.success('El código es correcto')
              window.location.assign(`https://${url}`);
            }else{
              this.msg.error('El código es incorrecto');
            }
          }

      });
    }

    render(){
     
      const {handleSubmit}=this.props;
        return (
            <div className="codelogin">
            <div className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
            </div>
            <div className="codeForm">
            <h3>Inserte el Código</h3>
            <form onSubmit={handleSubmit(this.handleSubmit.bind(this))} autoComplete="off">
                <Field
                name="code"
                type="number"
                component={renderField}
                label="Codigo"
                onChange={this.onInputChange}
                />
        
                <div>
                <button className="btn btn-primary" type="submit" >
                    Enviar
                </button>
                </div>
            </form>
            </div>
            <AlertContainer ref={a => this.msg = a} {...this.alertOptions} />
            </div>
        );
    }
}

function validate(values){
  const errors = {}
  if (!values.code) {
    errors.code = 'Por favor digite un código'
  }
  if (values.code){
    if(values.code.length<6 || values.code.length>8){
      errors.code = 'El código debe tener máximo 8 dígitos'
    }
  }
  
  return errors
}

function renderField(field){
  const {meta:{touched,error}} = field;
  const className = `form-group ${touched && error ? 'has-danger' : ''}`;
  
  return(
    <div className={className}>
    <div>
      <input
        className="input_code"
        maxLength="6"
        placeholder="Code"
        onFocus="true"
        type="numbers"
        label="Code"
        {...field.input}
        onChange={field.input.onChange}
        autoFocus
      />
      {touched &&
        ((error &&
          <span>
            {error}
          </span>))}

    </div>
    </div>
  );
}



function mapStateToProps(state){
  return {api:state.api};
}

export default reduxForm({
  validate,
  form: 'syncValidation' // a unique identifier for this form
   // <--- validation function given to redux-form
})(
  connect(mapStateToProps,{codeCorrect})(SyncValidationForm)
);