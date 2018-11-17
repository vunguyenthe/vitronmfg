import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CareerMySuffix from './career-my-suffix';
import CareerMySuffixDetail from './career-my-suffix-detail';
import CareerMySuffixUpdate from './career-my-suffix-update';
import CareerMySuffixDeleteDialog from './career-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CareerMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CareerMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CareerMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={CareerMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CareerMySuffixDeleteDialog} />
  </>
);

export default Routes;
