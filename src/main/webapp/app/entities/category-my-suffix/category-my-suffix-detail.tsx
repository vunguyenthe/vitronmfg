import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './category-my-suffix.reducer';
import { ICategoryMySuffix } from 'app/shared/model/category-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICategoryMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class CategoryMySuffixDetail extends React.Component<ICategoryMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { categoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Category [<b>{categoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{categoryEntity.name}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{categoryEntity.description}</dd>
            <dt>
              <span id="categoryImagePath">Category Image Path</span>
            </dt>
            <dd>
              {categoryEntity.categoryImagePath ? (
                <div>
                  <a onClick={openFile(categoryEntity.categoryImagePathContentType, categoryEntity.categoryImagePath)}>
                    <img
                      src={`data:${categoryEntity.categoryImagePathContentType};base64,${categoryEntity.categoryImagePath}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {categoryEntity.categoryImagePathContentType}, {byteSize(categoryEntity.categoryImagePath)}
                  </span>
                </div>
              ) : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/category-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/category-my-suffix/${categoryEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ category }: IRootState) => ({
  categoryEntity: category.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CategoryMySuffixDetail);
