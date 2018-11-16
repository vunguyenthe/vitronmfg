import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LogoMySuffix from './logo-my-suffix';
import LogoMySuffixDetail from './logo-my-suffix-detail';
import LogoMySuffixUpdate from './logo-my-suffix-update';
import LogoMySuffixDeleteDialog from './logo-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LogoMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LogoMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LogoMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={LogoMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LogoMySuffixDeleteDialog} />
  </>
);

export default Routes;
