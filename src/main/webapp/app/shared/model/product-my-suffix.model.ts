export interface IProductMySuffix {
  id?: number;
  title?: string;
  productImagePathContentType?: string;
  productImagePath?: any;
  detailedPdfPathContentType?: string;
  detailedPdfPath?: any;
  categoryId?: number;
}

export const defaultValue: Readonly<IProductMySuffix> = {};
