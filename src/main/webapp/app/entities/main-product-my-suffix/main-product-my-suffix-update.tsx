import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICategoryMySuffix } from 'app/shared/model/category-my-suffix.model';
import { getEntities as getCategories } from 'app/entities/category-my-suffix/category-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './main-product-my-suffix.reducer';
import { IMainProductMySuffix } from 'app/shared/model/main-product-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMainProductMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IMainProductMySuffixUpdateState {
  isNew: boolean;
  categoryId: number;
}

export class MainProductMySuffixUpdate extends React.Component<IMainProductMySuffixUpdateProps, IMainProductMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      categoryId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCategories();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { mainProductEntity } = this.props;
      const entity = {
        ...mainProductEntity,
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
    this.props.history.push('/entity/main-product-my-suffix');
  };

  render() {
    const { mainProductEntity, categories, loading, updating } = this.props;
    const { isNew } = this.state;

    const { photo, photoContentType } = mainProductEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vitronmfgApp.mainProduct.home.createOrEditLabel">Create or edit a MainProduct</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mainProductEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="main-product-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="titleLabel" for="title">
                    Title
                  </Label>
                  <AvField
                    id="main-product-my-suffix-title"
                    type="text"
                    name="title"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    Description
                  </Label>
                  <AvField id="main-product-my-suffix-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="photoLabel" for="photo">
                      Photo
                    </Label>
                    <br />
                    {photo ? (
                      <div>
                        <a onClick={openFile(photoContentType, photo)}>
                          <img src={`data:${photoContentType};base64,${photo}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {photoContentType}, {byteSize(photo)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('photo')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_photo" type="file" onChange={this.onBlobChange(true, 'photo')} accept="image/*" />
                    <AvInput type="hidden" name="photo" value={photo} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label for="category.id">Category</Label>
                  <AvInput id="main-product-my-suffix-category" type="select" className="form-control" name="categoryId">
                    <option value="" key="0" />
                    {categories
                      ? categories.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/main-product-my-suffix" replace color="info">
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
  categories: storeState.category.entities,
  mainProductEntity: storeState.mainProduct.entity,
  loading: storeState.mainProduct.loading,
  updating: storeState.mainProduct.updating
});

const mapDispatchToProps = {
  getCategories,
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
)(MainProductMySuffixUpdate);
