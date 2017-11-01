import React,{Component} from 'react';
import logo from '../logo.svg';
import '../App.css';
import Stores from '../images/apps.png';
import Scan from '../images/scan.png';

import {connect} from 'react-redux';
import {getQR} from '../actions';

class Steps extends Component{

    constructor(){
        super();
        this.handleAceptar = this.handleAceptar.bind(this);
        this.handleCancelar = this.handleCancelar.bind(this);

    }

    handleAceptar(){
        console.log(this.props.api);
        this.props.history.push('/administration');
    }

    handleCancelar(){
        this.props.history.push('/');
    }

    componentDidMount(){
        this.props.getQR();
    }


    render(){
        console.log(this.props.api.qr);
        return(
            <div>
                <div className="jumbotron">
                    <img src={logo} className="App-logo" alt="logo" />
                    <h1>TwoFactor Authentication</h1>      
                    <p>Por favor siga los siguientes pasos para activar el segundo factor</p>
                </div>
                <table className="step1 table-responsive">
                    <tbody>
                        <tr>
                            <td className="lineBreak">
                                <h5>Descargue la aplicación de Google Authenticator en la Play Store o en la App Store</h5>
                            </td>
                            <td className="apps">
                                <img src={Stores} alt="Play Store" className="media-object" width="400" height="200"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <h5>Seleccione la opción de ESCANEAR CÓDIGO</h5>
                            </td>
                            <td>
                                <img src={Scan} alt="Step" className="media-object" width="400" height="250"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <hr/>
                <table className="step2 table-responsive">
                    <tbody>
                        <tr>
                            <td>
                                <h5>Lee el siguiente código QR con la aplicación Google Authenticator</h5>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <img className="QRcode" src={`${this.props.api.qr}`} alt="QR" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <hr/>
                <button onClick={this.handleAceptar} className="aceptarModal btn btn-block btn-primary">Aceptar</button> 
                <button onClick={this.handleCancelar} className="cancelarModal btn btn-block  btn-danger">Cancelar</button>        
            </div>
            
        );
        
    }
}

function mapStateToProps(state){
    return {api:state.api};
}

export default connect(mapStateToProps,{getQR})(Steps);