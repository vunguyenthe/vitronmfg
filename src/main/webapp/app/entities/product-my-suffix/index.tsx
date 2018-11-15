import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductMySuffix from './product-my-suffix';
import ProductMySuffixDetail from './product-my-suffix-detail';
import ProductMySuffixUpdate from './product-my-suffix-update';
import ProductMySuffixDeleteDialog from './product-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProductMySuffixDeleteDialog} />
  </>
);

export default Routes;
