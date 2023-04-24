import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity('books', { schema: 'books' })
export class Books {
  @PrimaryGeneratedColumn({ type: 'bigint', name: 'id', unsigned: true })
  id: string;

  @Column('varchar', { name: 'title', length: 255 })
  title: string;

  @Column('varchar', { name: 'author', length: 255 })
  author: string;

  @Column('int', { name: 'publish_year' })
  publishYear: number;

  @Column('int', { name: 'page_count' })
  pageCount: number;

  @Column('timestamp', { name: 'created_at', nullable: true })
  createdAt: Date | null;

  @Column('timestamp', { name: 'updated_at', nullable: true })
  updatedAt: Date | null;
}
