import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './logo-my-suffix.reducer';
import { ILogoMySuffix } from 'app/shared/model/logo-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILogoMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class LogoMySuffixDetail extends React.Component<ILogoMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { logoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Logo [<b>{logoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="logoId">Logo Id</span>
            </dt>
            <dd>
              {logoEntity.logoId ? (
                <div>
                  <a onClick={openFile(logoEntity.logoIdContentType, logoEntity.logoId)}>
                    <img src={`data:${logoEntity.logoIdContentType};base64,${logoEntity.logoId}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {logoEntity.logoIdContentType}, {byteSize(logoEntity.logoId)}
                  </span>
                </div>
              ) : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/logo-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/logo-my-suffix/${logoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ logo }: IRootState) => ({
  logoEntity: logo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LogoMySuffixDetail);
