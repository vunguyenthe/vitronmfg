import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './career-my-suffix.reducer';
import { ICareerMySuffix } from 'app/shared/model/career-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICareerMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface ICareerMySuffixUpdateState {
  isNew: boolean;
}

export class CareerMySuffixUpdate extends React.Component<ICareerMySuffixUpdateProps, ICareerMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { careerEntity } = this.props;
      const entity = {
        ...careerEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/career-my-suffix');
  };

  render() {
    const { careerEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { newIconPath, newIconPathContentType, description, details } = careerEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vitronmfgApp.career.home.createOrEditLabel">Create or edit a Career</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : careerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="career-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="titleLabel" for="title">
                    Title
                  </Label>
                  <AvField
                    id="career-my-suffix-title"
                    type="text"
                    name="title"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="newIconPathLabel" for="newIconPath">
                      New Icon Path
                    </Label>
                    <br />
                    {newIconPath ? (
                      <div>
                        <a onClick={openFile(newIconPathContentType, newIconPath)}>
                          <img src={`data:${newIconPathContentType};base64,${newIconPath}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {newIconPathContentType}, {byteSize(newIconPath)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('newIconPath')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_newIconPath" type="file" onChange={this.onBlobChange(true, 'newIconPath')} accept="image/*" />
                    <AvInput type="hidden" name="newIconPath" value={newIconPath} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    Description
                  </Label>
                  <AvInput id="career-my-suffix-description" type="textarea" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="detailsLabel" for="details">
                    Details
                  </Label>
                  <AvInput id="career-my-suffix-details" type="textarea" name="details" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailContactLabel" for="emailContact">
                    Email Contact
                  </Label>
                  <AvField id="career-my-suffix-emailContact" type="text" name="emailContact" />
                </AvGroup>
                <AvGroup>
                  <Label id="mobileLabel" for="mobile">
                    Mobile
                  </Label>
                  <AvField id="career-my-suffix-mobile" type="text" name="mobile" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/career-my-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  careerEntity: storeState.career.entity,
  loading: storeState.career.loading,
  updating: storeState.career.updating
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CareerMySuffixUpdate);
