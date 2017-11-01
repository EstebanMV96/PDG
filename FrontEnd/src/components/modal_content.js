import React, {Component} from 'react';
import logo from '../logo4.svg';
import '../App.css';
import Stores from '../images/apps.png';
import Scan from '../images/scan.png';

import {connect} from 'react-redux';
import {getQR} from '../actions';

// import _ from 'lodash';

class ModalContent extends Component{

    render(){
    
        return(
            <div className="modal_div">
                <div className="App-header">
                    <img src={logo} className="App-logo" alt="logo" />
                    <h4 className="ModalPar">Por favor siga los siguientes pasos para activar el segundo factor</h4>
                </div>
                <table className="step1 table table-responsive">
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
                                <h5>Seleccione la opción ESCANEAR CÓDIGO DE BARRAS</h5>
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
                                <img className="QRcode" src={this.props.qr} alt="Codigo QR" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <hr/>
                <button onClick={this.props.handleModalAceptar} className="aceptarModal btn btn-lg btn-primary">Aceptar</button> 
                <button onClick={this.props.handleModalCancelar} className="cancelarModal btn btn-lg btn-danger">Cancelar</button>        
            </div>
              
        );
    }
}

function mapStateToProps(state){
    return {api:state.api};
}

export default connect(mapStateToProps,{getQR})(ModalContent);