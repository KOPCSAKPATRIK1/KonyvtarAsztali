import {
  Column,
  Entity,
  JoinColumn,
  ManyToOne,
  PrimaryGeneratedColumn,
} from 'typeorm';
import { Books } from './books.entity';

@Entity()
export class Rentals {
  @PrimaryGeneratedColumn()
  id: number;

  @ManyToOne(() => Books)
  @JoinColumn({ name: 'book_id' })
  book: Books;

  @Column()
  start_date: Date;

  @Column()
  end_date: Date;
}
