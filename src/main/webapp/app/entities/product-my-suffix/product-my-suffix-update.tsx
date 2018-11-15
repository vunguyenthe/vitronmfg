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
import { getEntity, updateEntity, createEntity, setBlob, reset } from './product-my-suffix.reducer';
import { IProductMySuffix } from 'app/shared/model/product-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IProductMySuffixUpdateState {
  isNew: boolean;
  categoryId: number;
}

export class ProductMySuffixUpdate extends React.Component<IProductMySuffixUpdateProps, IProductMySuffixUpdateState> {
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
      const { productEntity } = this.props;
      const entity = {
        ...productEntity,
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
    this.props.history.push('/entity/product-my-suffix');
  };

  render() {
    const { productEntity, categories, loading, updating } = this.props;
    const { isNew } = this.state;

    const { productImagePath, productImagePathContentType, detailedPdfPath, detailedPdfPathContentType } = productEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vitronmfgApp.product.home.createOrEditLabel">Create or edit a Product</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : productEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="product-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="titleLabel" for="title">
                    Title
                  </Label>
                  <AvField id="product-my-suffix-title" type="text" name="title" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="productImagePathLabel" for="productImagePath">
                      Product Image Path
                    </Label>
                    <br />
                    {productImagePath ? (
                      <div>
                        <a onClick={openFile(productImagePathContentType, productImagePath)}>
                          <img src={`data:${productImagePathContentType};base64,${productImagePath}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {productImagePathContentType}, {byteSize(productImagePath)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('productImagePath')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_productImagePath" type="file" onChange={this.onBlobChange(true, 'productImagePath')} accept="image/*" />
                    <AvInput type="hidden" name="productImagePath" value={productImagePath} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="detailedPdfPathLabel" for="detailedPdfPath">
                      Detailed Pdf Path
                    </Label>
                    <br />
                    {detailedPdfPath ? (
                      <div>
                        <a onClick={openFile(detailedPdfPathContentType, detailedPdfPath)}>Open</a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {detailedPdfPathContentType}, {byteSize(detailedPdfPath)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('detailedPdfPath')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_detailedPdfPath" type="file" onChange={this.onBlobChange(false, 'detailedPdfPath')} />
                    <AvInput type="hidden" name="detailedPdfPath" value={detailedPdfPath} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label for="category.id">Category</Label>
                  <AvInput id="product-my-suffix-category" type="select" className="form-control" name="categoryId">
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
                <Button tag={Link} id="cancel-save" to="/entity/product-my-suffix" replace color="info">
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
  productEntity: storeState.product.entity,
  loading: storeState.product.loading,
  updating: storeState.product.updating
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
)(ProductMySuffixUpdate);
