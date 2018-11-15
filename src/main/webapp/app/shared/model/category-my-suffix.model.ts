import { IProductMySuffix } from 'app/shared/model//product-my-suffix.model';
import { IMainProductMySuffix } from 'app/shared/model//main-product-my-suffix.model';

export interface ICategoryMySuffix {
  id?: number;
  name?: string;
  description?: any;
  categoryImagePathContentType?: string;
  categoryImagePath?: any;
  products?: IProductMySuffix[];
  mainProducts?: IMainProductMySuffix[];
}

export const defaultValue: Readonly<ICategoryMySuffix> = {};
