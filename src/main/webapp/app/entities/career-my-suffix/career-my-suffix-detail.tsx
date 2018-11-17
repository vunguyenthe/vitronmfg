import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './career-my-suffix.reducer';
import { ICareerMySuffix } from 'app/shared/model/career-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICareerMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class CareerMySuffixDetail extends React.Component<ICareerMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { careerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Career [<b>{careerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">Title</span>
            </dt>
            <dd>{careerEntity.title}</dd>
            <dt>
              <span id="newIconPath">New Icon Path</span>
            </dt>
            <dd>
              {careerEntity.newIconPath ? (
                <div>
                  <a onClick={openFile(careerEntity.newIconPathContentType, careerEntity.newIconPath)}>
                    <img
                      src={`data:${careerEntity.newIconPathContentType};base64,${careerEntity.newIconPath}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {careerEntity.newIconPathContentType}, {byteSize(careerEntity.newIconPath)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{careerEntity.description}</dd>
            <dt>
              <span id="details">Details</span>
            </dt>
            <dd>{careerEntity.details}</dd>
            <dt>
              <span id="emailContact">Email Contact</span>
            </dt>
            <dd>{careerEntity.emailContact}</dd>
            <dt>
              <span id="mobile">Mobile</span>
            </dt>
            <dd>{careerEntity.mobile}</dd>
          </dl>
          <Button tag={Link} to="/entity/career-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/career-my-suffix/${careerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ career }: IRootState) => ({
  careerEntity: career.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CareerMySuffixDetail);
