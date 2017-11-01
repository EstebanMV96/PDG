import React, {Component} from 'react';

import logo from '../logo4.svg';
import '../App.css';

import {connect} from 'react-redux';

//------ACTIONS----------//
import {listScratchCodes} from '../actions';
import {refreshScratchCodes} from '../actions';

import _ from 'lodash';

class Administration extends Component{

    constructor(props){
        super(props);
        this.handleDeactivate = this.handleDeactivate.bind(this);
        this.handleActualizar = this.handleActualizar.bind(this);
    }

    componentWillMount(){
        const id = this.props.match.params;         
        this.props.listScratchCodes(id.id);
    }

    handleDeactivate(){
        const id = this.props.match.params; 
        console.log(id);
    }

    handleActualizar(){
        const id = this.props.match.params; 
        console.log(id);
        this.props.refreshScratchCodes(id.id);        
    }

    renderScratchCodes(){
        const scratch = this.props.api.codes;
        console.log(scratch);
        if(scratch){
            return _.map(scratch.data, code =>{
                return (
                    <li className="list-group-item" key={code} >
                        {code}
                    </li>
                );
            });
        }

    }

    render(){
    
        return(
            <div>
                <div className="App-header">
                    <img src={logo} className="App-logo" alt="logo" />
                </div>
                <div id="administrative_div">
                    <table className="administrative_table">
                        <tbody>
                        <tr>
                            <td></td>
                            <td>
                            <button  className="deactive btn-activar btn-lg btn-danger" onClick={this.handleDeactivate}>Desactivar TwoFactor</button>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <h3>Códigos de Recuperación</h3>
                                <ul className="list-group">
                                    {this.renderScratchCodes()}
                                </ul>
                            </td>
                            <td></td> 
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                            <button  className="btn btn-success" onClick={this.handleActualizar}>Actualizar Códigos</button>
                            </td>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state){
    return {api:state.api};
}

export default connect(mapStateToProps,{listScratchCodes,refreshScratchCodes})(Administration);